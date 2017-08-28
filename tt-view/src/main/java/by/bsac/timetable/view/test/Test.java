/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsac.timetable.view.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import by.bsac.timetable.util.SupportClass;
import by.bsac.timetable.view.extra.EditForm;

/**
 *
 * @author hello
 */
public class Test {

    private static EditForm form;

    public static void main(String args[]) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Test obj = new Test();

                    form.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Test() {
        form = new EditForm(null, 1, 2, (byte)1);
        form.setBounds(100, 100, 590, 380);

    }
}
