// package fap_sports.integrador.models;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "tabla_de_posiciones")
// public class TablaPosiciones {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     // Relación con el campeonato (para saber a qué torneo pertenece esta posición)
//     @ManyToOne
//     @JoinColumn(name = "camp_id", nullable = false)
//     private Campeonato campeonato;

//     // Relación con el equipo (a quién representa esta posición en la tabla)
//     @ManyToOne
//     @JoinColumn(name = "equ_id", nullable = false) // <- CAMBIO AQUI
//     private Equipo equipo;

//     // Estadísticas
//     private int puntos;
//     private int partidosJugados;
//     private int partidosGanados;
//     private int partidosEmpatados;
//     private int partidosPerdidos;
//     private int golesFavor;
//     private int golesContra;

//     // Constructor vacío
//     public TablaPosiciones() {}

//     // Getters y Setters

//     public Long getId() {
//         return id;
//     }

//     public Campeonato getCampeonato() {
//         return campeonato;
//     }

//     public void setCampeonato(Campeonato campeonato) {
//         this.campeonato = campeonato;
//     }

//     public Equipo getEquipo() {
//         return equipo;
//     }

//     public void setEquipo(Equipo equipo) {
//         this.equipo = equipo;
//     }

//     public int getPuntos() {
//         return puntos;
//     }

//     public void setPuntos(int puntos) {
//         this.puntos = puntos;
//     }

//     public int getPartidosJugados() {
//         return partidosJugados;
//     }

//     public void setPartidosJugados(int partidosJugados) {
//         this.partidosJugados = partidosJugados;
//     }

//     public int getPartidosGanados() {
//         return partidosGanados;
//     }

//     public void setPartidosGanados(int partidosGanados) {
//         this.partidosGanados = partidosGanados;
//     }

//     public int getPartidosEmpatados() {
//         return partidosEmpatados;
//     }

//     public void setPartidosEmpatados(int partidosEmpatados) {
//         this.partidosEmpatados = partidosEmpatados;
//     }

//     public int getPartidosPerdidos() {
//         return partidosPerdidos;
//     }

//     public void setPartidosPerdidos(int partidosPerdidos) {
//         this.partidosPerdidos = partidosPerdidos;
//     }

//     public int getGolesFavor() {
//         return golesFavor;
//     }

//     public void setGolesFavor(int golesFavor) {
//         this.golesFavor = golesFavor;
//     }

//     public int getGolesContra() {
//         return golesContra;
//     }

//     public void setGolesContra(int golesContra) {
//         this.golesContra = golesContra;
//     }

//     // Método auxiliar: diferencia de goles
//     public int getDiferenciaGoles() {
//         return golesFavor - golesContra;
//     }
// }
