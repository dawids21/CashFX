module xyz.stasiak.cashfxdesktop {
    requires xyz.stasiak.cashfxcore;
    requires javafx.controls;
    requires javafx.fxml;


    opens xyz.stasiak.cashfx to javafx.fxml;
    exports xyz.stasiak.cashfx;
}