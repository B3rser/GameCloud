module com.mycompany.gamecloud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.json;

    opens com.mycompany.gamecloud to javafx.fxml;
    exports com.mycompany.gamecloud;
}
