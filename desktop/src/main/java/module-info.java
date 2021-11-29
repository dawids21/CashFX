module xyz.stasiak.cashfxdesktop {
    requires xyz.stasiak.cashfxcore;
    requires javafx.controls;
    requires javafx.fxml;
    requires io.vavr;


    opens xyz.stasiak.cashfx to javafx.fxml;
    exports xyz.stasiak.cashfx;
}