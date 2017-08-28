/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsac.timetable.util;

import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author hello
 * @param <T>
 */
public class CheckGeneralization<T> {

    public boolean checkRecordBeforeAdd(List<T> collection, T object) {
        for (T iterObj : collection) {
            if (iterObj.toString().equals(object.toString())) {
                JOptionPane.showMessageDialog(null, "Данная запись конфликтует с существующей");
                return false;
            }
        }
        return true;
    }
}
