package start;

import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

public class ReflectionExample {

    public static void retrieveProperties(Object object) {

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true); // set modifier to public
            Object value;
            try {
                value = field.get(object);
                System.out.println(field.getName() + "=" + value);

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    public static <Client> JTable createTable(List<Client> list) {
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();

        Class<?> crtClass = list.get(0).getClass();
        Field[] fields = crtClass.getDeclaredFields();

        Object[] columnsName = new Object[5];
        for (Field field : fields) {
            model.addColumn(field.getName());
        }

        for (Client client : list) {
            Object[] rowData = new Object[fields.length];

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                try {
                    rowData[i] = field.get(client);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(rowData);
        }

        table.setModel(model);

        return table;
    }
}
