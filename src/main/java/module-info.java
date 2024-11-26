module org.example.modul_122322 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.example.modul_122322 to javafx.fxml;
    exports org.example.modul_122322;
}