module Inicio{
        requires javafx.controls;
        requires javafx.graphics;
        requires javafx.fxml;
        requires java.desktop;
        requires javafx.swing;

        exports Componentes;
        exports grafos;
        exports tadCola;
        exports Controlador;
        exports listaCalificadaOrdenada;
        exports Modelo;
        exports Utilidad;

        opens Controlador;
        opens Componentes;
        opens grafos;
        opens tadCola;
        opens listaCalificadaOrdenada;
        opens Modelo;
        opens Utilidad;

        opens Inicio;
}