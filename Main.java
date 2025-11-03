import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.*;

public class Main extends JFrame {

    private JTextField pnrField;
    private JTextArea outputArea;
    private JCheckBox useApiCheck;
    private JButton checkButton;
    private JLabel statusLabel;

    // 🔑 Replace with your RapidAPI key
    private static final String API_KEY = "4226752969";

    // 🔗 API endpoint
    private static final String API_URL_TEMPLATE = "https://irctc1.p.rapidapi.com/api/v1/getPNRStatus?pnrNumber=%s";

    public Main() {
        setTitle("🚆 Indian Railway PNR Enquiry");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel header = new JLabel("🚆 Indian Railway PNR Enquiry", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel pnrLabel = new JLabel("Enter PNR:");
        pnrField = new JTextField(15);
        useApiCheck = new JCheckBox("Use Real API (RapidAPI)");
        checkButton = new JButton("Check Status");

        inputPanel.add(pnrLabel);
        inputPanel.add(pnrField);
        inputPanel.add(useApiCheck);
        inputPanel.add(checkButton);
        add(inputPanel, BorderLayout.NORTH);

        // Output Area
        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Status Label
        statusLabel = new JLabel("Ready");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // Button Action
        checkButton.addActionListener(e -> {
            String pnr = pnrField.getText().trim();

            if (pnr.isEmpty()) {
                JOptionPane.showMessageDialog(Main.this, "Please enter a PNR number!", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            outputArea.setText("Fetching data... Please wait.\n");
            statusLabel.setText("Fetching...");

            new Thread(() -> {
                try {
                    boolean useApi = useApiCheck.isSelected();
                    String result;

                    if (useApi) {
                        result = fetchFromApi(pnr);
                    } else {
                        result = getDemoResponse(pnr);
                    }

                    SwingUtilities.invokeLater(() -> {
                        outputArea.setText(result);
                        statusLabel.setText("✅ Success");
                    });

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        outputArea.setText("❌ Error: " + ex.getMessage());
                        statusLabel.setText("❌ Failed");
                    });
                }
            }).start();
        });
    }

    // 🔹 Demo data when API not used
    private String getDemoResponse(String pnr) {
        return """
                PNR: %s
                Train: HYDERABAD EXPRESS (12778)
                Date: 2025-10-12
                From: HYDERABAD DECAN (HYB)
                To: NAGPUR (NGP)
                Class: 3A
                Chart Prepared: No

                Passenger 1: CNF
                Passenger 2: CNF
                Passenger 3: WL/10 → CNF
                """.formatted(pnr);
    }

    // 🔹 Fetch live data from RapidAPI
    private String fetchFromApi(String pnr) throws Exception {
        String urlStr = String.format(API_URL_TEMPLATE, pnr);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("x-rapidapi-key", API_KEY);
        conn.setRequestProperty("x-rapidapi-host", "irctc1.p.rapidapi.com");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("API Error (HTTP " + responseCode + ")");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();

        JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();

        if (!json.has("data")) {
            return "❌ Invalid PNR or no data found.";
        }

        JsonObject data = json.getAsJsonObject("data");
        StringBuilder result = new StringBuilder();
        result.append("PNR: ").append(pnr).append("\n");
        result.append("Train: ").append(data.get("train_name").getAsString())
              .append(" (").append(data.get("train_number").getAsString()).append(")\n");
        result.append("Date: ").append(data.get("train_start_date").getAsString()).append("\n");
        result.append("From: ").append(data.get("from_station_name").getAsString())
              .append(" → To: ").append(data.get("to_station_name").getAsString()).append("\n");
        result.append("Class: ").append(data.get("class").getAsString()).append("\n");
        result.append("Chart Prepared: ").append(data.get("chart_prepared").getAsString()).append("\n\n");

        JsonArray passengers = data.getAsJsonArray("passengers");
        for (JsonElement elem : passengers) {
            JsonObject p = elem.getAsJsonObject();
            result.append("Passenger ").append(p.get("no").getAsInt()).append(": ")
                  .append(p.get("current_status").getAsString())
                  .append(" (").append(p.get("booking_status").getAsString()).append(")\n");
        }

        return result.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}