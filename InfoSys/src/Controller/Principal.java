package Controller;

import Util.Configura;
import View.TelaCadastro;

public class Principal {
    public static void main(String[] args) {
        Configura.LookAndFeel("Windows Classic");
        new TelaCadastro().setVisible(true);
        dao.ConnectionFactory.getConnection();
    }
}
