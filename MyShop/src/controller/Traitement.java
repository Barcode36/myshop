/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 *
 * @author Christ
 */
public abstract class Traitement {

    public static boolean isTextFieldNotEmpty(JFXTextField textField) {
        boolean bon = false;
        if (textField.getText().length() != 0 || !textField.getText().isEmpty()) {
            bon = true;
        }
        return bon;
    }

    public static boolean isTextFieldNotEmpty(JFXTextField textField, Label label, String msgErrror) {
        boolean bon = false;
        String msg = null;
        if (!isTextFieldNotEmpty(textField)) {
            bon = true;
            msg = msgErrror;
        }
        label.setText(msg);
        return bon;
    }

    public static boolean isPassTextFieldNotEmpty(JFXPasswordField textField) {
        boolean bon = false;
        if (textField.getText().length() != 0 || !textField.getText().isEmpty()) {
            bon = true;
        }
        return bon;
    }

    public static boolean isTextPassFieldNotEmpty(JFXPasswordField textField, Label label, String msgErrror) {
        boolean bon = false;
        String msg = null;
        if (!isPassTextFieldNotEmpty(textField)) {
            bon = true;
            msg = msgErrror;
        }
        label.setText(msg);
        return bon;
    }

    public static boolean textFieldTypeNumber(JFXTextField textField) {
        boolean bon = false;
        if (textField.getText().matches("([0-9]+(\\.[0-9]+)?)+")) {
            bon = true;
        }
        return bon;
    }
    public static boolean textFieldTypeNumber(TextField textField) {
        boolean bon = false;
        if (textField.getText().matches("([0-9]+(\\.[0-9]+)?)+")) {
            bon = true;
        }
        return bon;
    }

    public static boolean textFieldTypeNumber(JFXTextField textField, Label label, String msgErrror) {
        boolean bon = false;
        String msg = null;
        if (!textFieldTypeNumber(textField)) {
            bon = true;
            msg = msgErrror;
        }
        label.setText(msg);
        return bon;
    }

    public static boolean comboEmty(JFXComboBox<?> comboBox) {
        boolean bon = false;
        if (comboBox.getValue() != null) {
            bon = true;
        }
        return bon;
    }

    public static boolean comboNotEmty(JFXComboBox<?> comboBox, Label label, String msgErrror) {
        boolean bon = false;
        String msg = null;
        if (!comboEmty(comboBox)) {
            bon = true;
            msg = msgErrror;
        }
        label.setText(msg);
        return bon;
    }

    public static boolean datePickerEmty(JFXDatePicker picker) {
        boolean bon = false;
        if (picker.getValue() != null) {
            bon = true;
        }
        return bon;
    }

    public static boolean datePickerNotEmty(JFXDatePicker picker, Label label, String msgErrror) {
        boolean bon = false;
        String msg = null;
        if (!datePickerEmty(picker)) {
            bon = true;
            msg = msgErrror;
        }
        label.setText(msg);
        return bon;
    }

    public static boolean ratioEmty(RadioButton radioButton1, RadioButton radioButton2) {
        boolean bon = false;
        if (radioButton1.isSelected() || radioButton2.isSelected()) {
            bon = true;
        }
        return bon;
    }

    public static boolean ratioNotEmty(RadioButton radioButton1, RadioButton radioButton2, Label label, String msgErrror) {
        boolean bon = false;
        String msg = null;
        if (!ratioEmty(radioButton1, radioButton2)) {
            bon = true;
            msg = msgErrror;
        }
        label.setText(msg);
        return bon;
    }

    public static boolean listEmpty(ObservableList<?> listMard) {
        boolean bon = false;
        if (!listMard.isEmpty()) {
            bon = true;
        }
        return bon;
    }

    public static boolean listNotEmpty(ObservableList<?> listMard, Label label, String msgErrror) {
        boolean bon = false;
        String msg = null;
        if (!listEmpty(listMard)) {
            bon = true;
            msg = msgErrror;
        }
        label.setText(msg);
        return bon;
    }
}
