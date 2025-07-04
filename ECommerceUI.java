import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class ECommerceUI {
    private List<Product> productList = new ArrayList<>();
    private ShoppingCart cart = new ShoppingCart();

    public ECommerceUI() {
        // Add sample products to the list
        productList.add(new Product("P001", "Laptop", 59999));
        productList.add(new Product("P002", "Smartphone", 19999));
        productList.add(new Product("P003", "Headphones", 2999));
        productList.add(new Product("P004", "Monitor", 9999));
        productList.add(new Product("P005", "Keyboard", 1499));

        createUI();
    }

    private void createUI() {
        JFrame frame = new JFrame("🛒 E-Commerce Shopping Cart");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);

        JComboBox<Product> productCombo = new JComboBox<>();
        for (Product p : productList) {
            productCombo.addItem(p);
        }

        JTextField quantityField = new JTextField("1");
        JButton addToCartBtn = new JButton("Add to Cart");
        JButton removeBtn = new JButton("Remove by ID");
        JTextField removeField = new JTextField();
        JButton viewCartBtn = new JButton("View Cart");
        JButton checkoutBtn = new JButton("Checkout");
        JButton clearBtn = new JButton("Clear Cart");

        JPanel topPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        topPanel.add(new JLabel("Select Product:"));
        topPanel.add(productCombo);
        topPanel.add(new JLabel("Quantity:"));
        topPanel.add(quantityField);
        topPanel.add(addToCartBtn);
        topPanel.add(viewCartBtn);
        topPanel.add(new JLabel("Remove Product ID:"));
        topPanel.add(removeField);
        topPanel.add(removeBtn);
        topPanel.add(checkoutBtn);
        topPanel.add(clearBtn);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Action Listeners
        addToCartBtn.addActionListener(e -> {
            try {
                Product selected = (Product) productCombo.getSelectedItem();
                int qty = Integer.parseInt(quantityField.getText());
                cart.addProduct(selected, qty);
                displayArea.append("✅ Added: " + selected.getName() + " x" + qty + "\n");
            } catch (Exception ex) {
                displayArea.append("❌ Error adding product. Check quantity input.\n");
            }
        });

        viewCartBtn.addActionListener(e -> {
            displayArea.append("\n🛒 Your Cart:\n");
            for (CartItem item : cart.getItems()) {
                displayArea.append(item.getProduct().getName() + " x" + item.getQuantity() +
                    " = ₹" + item.getTotalPrice() + "\n");
            }
            displayArea.append("Total: ₹" + cart.getTotal() + "\n\n");
        });

        removeBtn.addActionListener(e -> {
            String id = removeField.getText().trim();
            cart.removeProduct(id);
            displayArea.append("🗑️ Removed product with ID: " + id + "\n");
        });

        checkoutBtn.addActionListener(e -> {
            displayArea.append("\n💰 Checkout complete!\nTotal paid: ₹" + cart.getTotal() + "\nThank you!\n\n");
            cart.clearCart();
        });

        clearBtn.addActionListener(e -> {
            cart.clearCart();
            displayArea.setText("🧹 Cart cleared.\n");
        });

        frame.setVisible(true);
    }
}
