module org.example.modul_122322 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires batik.transcoder;
    requires javafx.swing;

    opens org.example.modul_122322 to javafx.fxml;
    exports org.example.modul_122322;
}