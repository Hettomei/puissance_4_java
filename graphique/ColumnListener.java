package graphique;

import java.util.EventListener;

public interface ColumnListener extends EventListener {
    void columnHover();
    void columnExited();
}
