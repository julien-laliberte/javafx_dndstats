module dursahn.dndstats {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;


    opens dursahn.dndstats to javafx.fxml;
    exports dursahn.dndstats;
    exports dursahn.dndstats.controllers;
    opens dursahn.dndstats.controllers to javafx.fxml;
}