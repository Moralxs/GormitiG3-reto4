import pymysql
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.gridspec as gridspec
import numpy as np

# ─────────────────────────────────────────────
#  CONEXIÓN
# ─────────────────────────────────────────────
DB_CONFIG = {
    "host":     "127.0.0.1",
    "user":     "root",
    "password": "root",
    "database": "sistema_intercambio",
    "port":     3306
}

def conectar():
    conn = pymysql.connect(**DB_CONFIG)
    print("✅ Conexión exitosa")
    return conn

# ─────────────────────────────────────────────
#  CONSULTAS — solo datos reales
# ─────────────────────────────────────────────
def cargar_datos():
    conn = conectar()

    # 1. Distribución de géneros y edades de personas
    df_personas = pd.read_sql("""
        SELECT edad, genero
        FROM persona
    """, conn)

    # 2. Roles de usuarios y su experiencia
    df_usuarios = pd.read_sql("""
        SELECT u.rol, u.experiencia, u.numMovimientos
        FROM usuario u
    """, conn)

    # 3. Estado y tipo de materiales
    df_materiales = pd.read_sql("""
        SELECT tipoMaterial, estadoMaterial
        FROM material
    """, conn)

    # 4. Temperatura y consumo de intercambiadores
    df_intercambiadores = pd.read_sql("""
        SELECT temperaturaEntrada, temperaturaSalida, consumo, tiempoEncendido
        FROM intercambiador
    """, conn)

    # 5. Costo y carga de trabajo de centros de datos
    df_centros = pd.read_sql("""
        SELECT id_centro, costo, carga_de_trabajo
        FROM centro_de_datos
    """, conn)

    conn.close()
    return df_personas, df_usuarios, df_materiales, df_intercambiadores, df_centros

# ─────────────────────────────────────────────
#  ESTILO
# ─────────────────────────────────────────────
FONDO    = "#0f0f1a"
PANEL    = "#1a1a2e"
TEXTO    = "#eaeaea"
PALETA   = ["#e94560", "#00d4aa", "#ffd166", "#06aed5", "#f07d13", "#a855f7"]

def estilo(ax, titulo):
    ax.set_facecolor(PANEL)
    ax.tick_params(colors=TEXTO, labelsize=9)
    for spine in ax.spines.values():
        spine.set_edgecolor("#333355")
    ax.set_title(titulo, color=TEXTO, fontsize=11, fontweight="bold", pad=12)
    ax.xaxis.label.set_color(TEXTO)
    ax.yaxis.label.set_color(TEXTO)

# ─────────────────────────────────────────────
#  GRÁFICAS
# ─────────────────────────────────────────────
def graficar(df_personas, df_usuarios, df_materiales, df_intercambiadores, df_centros):

    fig = plt.figure(figsize=(18, 20), facecolor=FONDO)
    fig.suptitle("Dashboard — Sistema de Intercambio", color=TEXTO,
                 fontsize=16, fontweight="bold", y=0.98)

    gs = gridspec.GridSpec(3, 2, figure=fig,
                           hspace=0.5, wspace=0.35,
                           left=0.07, right=0.96, top=0.94, bottom=0.05)

    # ── GRÁFICA 1: Distribución de edad por género (boxplot) ─────────────
    ax1 = fig.add_subplot(gs[0, 0])
    estilo(ax1, "1. Edad por Género")

    generos = df_personas["genero"].unique()
    datos_box = [df_personas[df_personas["genero"] == g]["edad"].values for g in generos]
    bp = ax1.boxplot(datos_box, patch_artist=True, labels=generos,
                     medianprops=dict(color=FONDO, linewidth=2))
    for patch, color in zip(bp["boxes"], PALETA):
        patch.set_facecolor(color)
        patch.set_alpha(0.85)
    for whisker in bp["whiskers"] + bp["caps"]:
        whisker.set_color(TEXTO)
    for flier in bp["fliers"]:
        flier.set(marker="o", color=PALETA[0], alpha=0.6)
    ax1.set_ylabel("Edad")
    ax1.set_xlabel("Género")

    # ── GRÁFICA 2: Experiencia por rol (barras agrupadas) ─────────────────
    ax2 = fig.add_subplot(gs[0, 1])
    estilo(ax2, "2. Experiencia y Movimientos por Rol")

    roles = df_usuarios.groupby("rol")[["experiencia", "numMovimientos"]].mean().reset_index()
    x = np.arange(len(roles))
    w = 0.35
    b1 = ax2.bar(x - w/2, roles["experiencia"],   width=w, color=PALETA[0],
                 label="Experiencia media",  edgecolor=FONDO)
    b2 = ax2.bar(x + w/2, roles["numMovimientos"], width=w, color=PALETA[1],
                 label="Movimientos medios", edgecolor=FONDO)
    ax2.set_xticks(x)
    ax2.set_xticklabels(roles["rol"], color=TEXTO)
    ax2.legend(fontsize=8, labelcolor=TEXTO, facecolor="#0f0f1a", edgecolor="none")
    ax2.set_ylabel("Valor medio")
    for bar in list(b1) + list(b2):
        ax2.text(bar.get_x() + bar.get_width()/2, bar.get_height() + 0.5,
                 f"{bar.get_height():.0f}", ha="center", color=TEXTO, fontsize=8)

    # ── GRÁFICA 3: Estado de materiales por tipo (barras apiladas) ────────
    ax3 = fig.add_subplot(gs[1, 0])
    estilo(ax3, "3. Estado de Materiales por Tipo")

    tabla = pd.crosstab(df_materiales["tipoMaterial"], df_materiales["estadoMaterial"])
    bottom = np.zeros(len(tabla))
    for i, col in enumerate(tabla.columns):
        bars = ax3.bar(tabla.index, tabla[col], bottom=bottom,
                       color=PALETA[i % len(PALETA)], label=col, edgecolor=FONDO)
        for bar, bot in zip(bars, bottom):
            h = bar.get_height()
            if h > 0:
                ax3.text(bar.get_x() + bar.get_width()/2, bot + h/2,
                         str(int(h)), ha="center", va="center",
                         color=FONDO, fontsize=8, fontweight="bold")
        bottom += tabla[col].values
    ax3.legend(fontsize=8, labelcolor=TEXTO, facecolor="#0f0f1a",
               edgecolor="none", loc="upper right")
    ax3.set_xlabel("Tipo Material")
    ax3.set_ylabel("Cantidad")
    plt.setp(ax3.get_xticklabels(), rotation=15, ha="right")

    # ── GRÁFICA 4: Temperatura entrada vs salida (scatter) ────────────────
    ax4 = fig.add_subplot(gs[1, 1])
    estilo(ax4, "4. Temperatura Entrada vs Salida")

    sc = ax4.scatter(
        df_intercambiadores["temperaturaEntrada"],
        df_intercambiadores["temperaturaSalida"],
        c=df_intercambiadores["consumo"],
        cmap="plasma", s=120, alpha=0.9,
        edgecolors="white", linewidths=0.5
    )
    # línea diagonal de referencia (entrada == salida)
    lmin = min(df_intercambiadores[["temperaturaEntrada","temperaturaSalida"]].min())
    lmax = max(df_intercambiadores[["temperaturaEntrada","temperaturaSalida"]].max())
    ax4.plot([lmin, lmax], [lmin, lmax], "--", color=PALETA[0], alpha=0.5, linewidth=1.2)

    cb = fig.colorbar(sc, ax=ax4, pad=0.02, shrink=0.85)
    cb.set_label("Consumo (W)", color=TEXTO, fontsize=8)
    cb.ax.yaxis.set_tick_params(color=TEXTO)
    plt.setp(cb.ax.yaxis.get_ticklabels(), color=TEXTO, fontsize=7)
    ax4.set_xlabel("T° Entrada (°C)")
    ax4.set_ylabel("T° Salida (°C)")

    # ── GRÁFICA 5: Costo vs Carga de trabajo por centro ───────────────────
    ax5 = fig.add_subplot(gs[2, :])
    estilo(ax5, "5. Costo vs Carga de Trabajo por Centro de Datos")

    x5 = np.arange(len(df_centros))
    ax5b = ax5.twinx()

    b_costo = ax5.bar(x5 - 0.2, df_centros["costo"], width=0.4,
                      color=PALETA[2], label="Costo (€)", edgecolor=FONDO, alpha=0.9)
    b_carga = ax5b.bar(x5 + 0.2, df_centros["carga_de_trabajo"], width=0.4,
                       color=PALETA[3], label="Carga trabajo", edgecolor=FONDO, alpha=0.9)

    ax5.set_xticks(x5)
    ax5.set_xticklabels([f"Centro {i}" for i in df_centros["id_centro"]], color=TEXTO)
    ax5.set_ylabel("Costo (€)", color=PALETA[2])
    ax5b.set_ylabel("Carga de Trabajo", color=PALETA[3])
    ax5b.tick_params(colors=TEXTO, labelsize=9)
    ax5b.set_facecolor(PANEL)
    for spine in ax5b.spines.values():
        spine.set_edgecolor("#333355")

    lines1, labels1 = ax5.get_legend_handles_labels()
    lines2, labels2 = ax5b.get_legend_handles_labels()
    ax5.legend(lines1 + lines2, labels1 + labels2, fontsize=8,
               labelcolor=TEXTO, facecolor="#0f0f1a", edgecolor="none")

    plt.savefig("dashboard_gormiti.png", dpi=150,
                bbox_inches="tight", facecolor=FONDO)
    print("✅ Gráficas guardadas en 'dashboard_gormiti.png'")
    plt.show()


# ─────────────────────────────────────────────
#  MAIN
# ─────────────────────────────────────────────
if __name__ == "__main__":
    df_p, df_u, df_m, df_i, df_c = cargar_datos()
    graficar(df_p, df_u, df_m, df_i, df_c)
