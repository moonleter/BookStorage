module com.example.semeestralniukolkunz {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.osu.semeestralniukolkunz.model to javafx.fxml;
    exports com.osu.semeestralniukolkunz.model;
    exports com.osu.semeestralniukolkunz.view;
    opens com.osu.semeestralniukolkunz.view to javafx.fxml;
    opens com.osu.semeestralniukolkunz.controller to javafx.fxml;
    exports com.osu.semeestralniukolkunz.controller;
    exports com.osu.semeestralniukolkunz.utility;
    opens com.osu.semeestralniukolkunz.utility to javafx.fxml;
}