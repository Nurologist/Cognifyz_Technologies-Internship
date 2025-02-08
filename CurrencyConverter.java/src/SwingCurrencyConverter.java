import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class SwingCurrencyConverter {

    public static void main(String[] args) {
        // Create JFrame
        JFrame frame = new JFrame("Currency Converter");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 2, 10, 10));

        // Create UI components
        JLabel baseCurrencyLabel = new JLabel("Base Currency (e.g., USD):");
        JTextField baseCurrencyField = new JTextField();
        JLabel targetCurrencyLabel = new JLabel("Target Currency (e.g., INR):");
        JTextField targetCurrencyField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        JButton convertButton = new JButton("Convert");
        JButton clearButton = new JButton("Clear");
        JLabel resultLabel = new JLabel("Converted Amount:");
        JTextField resultField = new JTextField();
        resultField.setEditable(false);

        // Add components to frame
        frame.add(baseCurrencyLabel);
        frame.add(baseCurrencyField);
        frame.add(targetCurrencyLabel);
        frame.add(targetCurrencyField);
        frame.add(amountLabel);
        frame.add(amountField);
        frame.add(convertButton);
        frame.add(clearButton);
        frame.add(resultLabel);
        frame.add(resultField);

        // Action Listener for Convert Button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String baseCurrency = baseCurrencyField.getText().toUpperCase().trim();
                String targetCurrency = targetCurrencyField.getText().toUpperCase().trim();
                String amountText = amountField.getText().trim();

                try {
                    // Validate input
                    if (baseCurrency.isEmpty() || targetCurrency.isEmpty() || amountText.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double amount = Double.parseDouble(amountText);

                    // Fetch exchange rate and calculate
                    double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);
                    if (exchangeRate == -1) {
                        JOptionPane.showMessageDialog(frame, "Failed to retrieve exchange rate.", "API Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double convertedAmount = amount * exchangeRate;
                    resultField.setText(String.format("%.2f %s", convertedAmount, targetCurrency));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid amount. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action Listener for Clear Button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                baseCurrencyField.setText("");
                targetCurrencyField.setText("");
                amountField.setText("");
                resultField.setText("");
            }
        });

        // Set frame visibility
        frame.setVisible(true);
    }

    /**
     * Fetches the exchange rate from a public API.
     *
     * @param baseCurrency   The base currency code (e.g., USD).
     * @param targetCurrency The target currency code (e.g., EUR).
     * @return The exchange rate, or -1 if an error occurs.
     */
    private static double fetchExchangeRate(String baseCurrency, String targetCurrency) {
        String apiUrl = String.format("https://api.exchangerate.host/latest?base=%s&symbols=%s", baseCurrency, targetCurrency);

        try {
            // Create HTTP client and request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            // Send request and get response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(response.body());
            if (jsonResponse.has("rates")) {
                return jsonResponse.getJSONObject("rates").getDouble(targetCurrency);
            }
        } catch (Exception e) {
            System.out.println("Error fetching exchange rate: " + e.getMessage());
        }
        return -1;
    }
}
