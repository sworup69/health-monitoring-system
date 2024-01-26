package org.healthmonitoring;

import javax.swing.*;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.util.List;

public class ClientGUI {
    ClientSock sock;
    JPanel recordsList = new JPanel(new GridLayout(0, 1, 10, 10));

    public ClientGUI(ClientSock sock) {
        this.sock = sock;
    }

    void show() {
        Dimension frameSize = new Dimension(1400, 700);
        JFrame frame = new JFrame("Health Monitoring System");
        frame.setSize(frameSize);
        frame.setMinimumSize(frameSize);

        JPanel container = new JPanel(new BorderLayout());

        int fieldSize = 15;
        JPanel controls = new JPanel();
        JTextField userId = new JTextField(fieldSize);
        JTextField weight = new JTextField(fieldSize);
        JTextField exercise = new JTextField(fieldSize);
        JTextField timestamp = new JTextField("YYYY-MM-DD", fieldSize);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            try {
                this.sock.outputStream.writeObject(new HealthRecordAddMsg(
                        new HealthRecord(
                                Integer.parseInt(userId.getText()),
                                Double.parseDouble(weight.getText()),
                                exercise.getText(),
                                timestamp.getText()
                        )
                ));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton listRecordsButton = new JButton("List Health Records");
        listRecordsButton.addActionListener(e -> {
            try {
                this.sock.outputStream.writeObject(new HealthRecordsRequest());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        controls.add(new JLabel("User Id"));
        controls.add(userId);
        controls.add(new JLabel("Weight"));
        controls.add(weight);
        controls.add(new JLabel("Exercise"));
        controls.add(exercise);
        controls.add(new JLabel("Timestamp"));
        controls.add(timestamp);

        controls.add(addButton);
        controls.add(listRecordsButton);

        container.add(controls, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(this.recordsList);
        container.add(scrollPane, BorderLayout.CENTER);

        frame.add(container);
        frame.setVisible(true);
    }

    void setHealthRecords(List<HealthRecord> records) {
        this.recordsList.removeAll();
        for (HealthRecord r : records) {
            System.out.println("Adding event " + r);
            JPanel panel = new JPanel();
            panel.add(new JLabel("User Id: " + r.userId));
            panel.add(new JLabel("Weight: " + r.weight));
            panel.add(new JLabel("Exercise: " + r.exercise));
            panel.add(new JLabel("Timestamp: " + r.timestamp));
            this.recordsList.add(panel);
        }
        this.recordsList.revalidate();
        this.recordsList.repaint();
    }

    void listen() {
        System.out.println("Listening for server messages");
        while (true) {
            try {
                Object res = this.sock.inputStream.readObject();
                System.out.println(res);

                if (res instanceof HealthRecordsResponse msg) {
                    setHealthRecords(msg.records);
                }
            }
            catch (EOFException e) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error while reading server messages");
            }
        }
    }
}