module com.mycompany.ps2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens interfac to javafx.fxml;
    exports interfac;
    exports treillis;
    exports Noeuds;
    exports syslin;
}
