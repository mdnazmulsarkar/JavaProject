import java.util.*;

class User {
    private String username; 
    private String password; 

  
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Product {
    private int id; 
    private String name;
    private double price;
    private int stock; 
    private List<String> reviews;

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reviews = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void addReview(String review) {
        reviews.add(review); 
    }

    public List<String> getReviews() {
        return reviews; 
    }

   
    @Override
    public String toString() {
        return "Product ID: " + id + ", Name: " + name + ", Price: $" + price + ", Stock: " + stock;
    }
}

class CartItem {
    private Product product;
    private int quantity; 

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    
    public double getTotalPrice() {
        return product.getPrice() * quantity; 
    }

    
    @Override
    public String toString() {
        return product.getName() + " x " + quantity + " = $" + String.format("%.2f", getTotalPrice());
    }
}


class Cart {
    private List<CartItem> items;
    
    public Cart() {
        items = new ArrayList<>();
    }

    public void addProduct(Product product, int quantity) {
        if (product.getStock() < quantity) {
            System.out.println("Insufficient stock for product: " + product.getName());
            return;
        }
        product.setStock(product.getStock() - quantity);
        items.add(new CartItem(product, quantity));
        System.out.println("Added " + quantity + " x " + product.getName() + " to the cart.");
    }

    
    public double getTotal() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum(); 
    }
    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        items.forEach(System.out::println); 
        System.out.println("Total: $" + String.format("%.2f", getTotal())); 
    }

  
    public void clearCart() {
        items.clear(); 
    }
}

public class Shopymerce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<User> users = new ArrayList<>(); 
        List<Product> products = new ArrayList<>();
        Cart cart = new Cart(); 
        User currentUser = null; 
        products.add(new Product(1, "Laptop", 1000.00, 10));
        products.add(new Product(2, "Smartphone", 700.00, 20));
        products.add(new Product(3, "Headphones", 50.00, 100));
        products.add(new Product(4, "Keyboard", 30.00, 50));
        products.add(new Product(5, "Mouse", 20.00, 60));
        products.add(new Product(6, "Monitor", 150.00, 15));
        products.add(new Product(7, "Printer", 120.00, 12));
        products.add(new Product(8, "Tablet", 300.00, 25));
        products.add(new Product(9, "Router", 80.00, 30));
        products.add(new Product(10, "External Hard Drive", 100.00, 40));

        System.out.println("Welcome to Shopymerce!");

        while (true) {
            if (currentUser == null) {
                System.out.println("\n1. Register\n2. Login\n3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        users.add(new User(username, password)); 
                        System.out.println("Registration successful!");
                        break;
                    case 2:
                        System.out.print("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        Optional<User> user = users.stream().filter(u -> u.getUsername().equals(loginUsername) && u.getPassword().equals(loginPassword)).findFirst();
                        if (user.isPresent()) {
                            currentUser = user.get(); 
                            System.out.println("Login successful! Welcome, " + currentUser.getUsername());
                        } else {
                            System.out.println("Invalid username or password.");
                        }
                        break;
                    case 3:
                        System.out.println("Thank you for visiting Shopymerce! Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                
                System.out.println("\n1. View Products\n2. Add to Cart\n3. View Cart\n4. Add Product Review\n5. Checkout\n6. Logout\n7. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("\nAvailable Products:");
                        products.forEach(System.out::println);
                        break;
                    case 2:
                        System.out.print("Enter Product ID to add to cart: ");
                        int productId = scanner.nextInt();
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();
                        Optional<Product> product = products.stream().filter(p -> p.getId() == productId).findFirst();
                        if (product.isPresent()) {
                            cart.addProduct(product.get(), quantity); 
                        } else {
                            System.out.println("Invalid Product ID.");
                        }
                        break;
                    case 3:
                        System.out.println("\nYour Cart:");
                        cart.displayCart();
                        break;
                    case 4:
                        System.out.print("Enter Product ID to review or view reviews: ");
                        int reviewProductId = scanner.nextInt();
                        scanner.nextLine();
                        Optional<Product> reviewProduct = products.stream().filter(p -> p.getId() == reviewProductId).findFirst();
                        if (reviewProduct.isPresent()) {
                            System.out.println("\n1. Add Review\n2. View Reviews");
                            System.out.print("Choose an option: ");
                            int reviewChoice = scanner.nextInt();
                            scanner.nextLine();
                            if (reviewChoice == 1) {
                                System.out.print("Enter your review: ");
                                String review = scanner.nextLine();
                                reviewProduct.get().addReview(review);
                                System.out.println("Review added successfully!");
                            } else if (reviewChoice == 2) {
                                List<String> reviews = reviewProduct.get().getReviews();
                                if (reviews.isEmpty()) {
                                    System.out.println("No reviews available for this product.");
                                } else {
                                    System.out.println("Reviews for " + reviewProduct.get().getName() + ":");
                                    reviews.forEach(r -> System.out.println("- " + r));
                                }
                            } else {
                                System.out.println("Invalid option. Returning to menu.");
                            }
                        } else {
                            System.out.println("Invalid Product ID. Please try again.");
                        }
                        break;
                    case 5:
                        System.out.println("\nCheckout Total: $" + String.format("%.2f", cart.getTotal()));
                        System.out.println("Choose Payment Method:");
                        System.out.println("1. Credit Card\n2. Debit Card");
                        System.out.print("Select: ");
                        int paymentChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (paymentChoice) {
                            case 1:
                                System.out.println("Payment successful via Credit Card. Thank you!");
                                break;
                            case 2:
                                System.out.println("Payment successful via Debit Card. Thank you!");
                                break;
                        }
                        cart.clearCart(); 
                        break;
                    case 6:
                        currentUser = null;
                        System.out.println("Logged out successfully.");
                        break;
                    case 7:
                        System.out.println("Thank you for visiting Shopymerce! Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}
