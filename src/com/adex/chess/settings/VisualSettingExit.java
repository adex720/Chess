package com.adex.chess.settings;

import com.adex.chess.visual.Panel;

public class VisualSettingExit extends VisualSetting{

    public VisualSettingExit() {
        super("Exit", 1, 1, 1, false, false);
    }

    @Override
    public void pressed(Panel panel) {
        panel.saveSettings();
    }
}
