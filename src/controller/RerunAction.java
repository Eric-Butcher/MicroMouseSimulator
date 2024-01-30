package controller;

import model.Model;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RerunAction implements ActionListener {
    private final Model model;
    private final View view;
    private final Controller controller;

    public RerunAction(Model model, View view, Controller controller){
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        this.controller.playPause();
        this.model.rerun();
        this.view.updateView(this.model.updateView());
    }
}
