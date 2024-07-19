import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Book {
    private String title;
    private String author;
    private boolean checkedOut;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.checkedOut = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Status: " + (checkedOut ? "Checked Out" : "Available");
    }
}

class LibraryCatalog {
    private ArrayList<Book> books;

    public LibraryCatalog() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Book searchByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public boolean checkOutBook(String title) {
        Book book = searchByTitle(title);
        if (book != null && !book.isCheckedOut()) {
            book.setCheckedOut(true);
            return true;
        }
        return false;
    }

    public boolean returnBook(String title) {
        Book book = searchByTitle(title);
        if (book != null && book.isCheckedOut()) {
            book.setCheckedOut(false);
            return true;
        }
        return false;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}

public class cextends JFrame {
    private LibraryCatalog libraryCatalog;
    private JPanel addBookPanel, searchBookPanel;
    private JTextField titleField, authorField, searchField;
    private JTextArea displayArea;

    public LibraryManagementSystem() {
        libraryCatalog = new LibraryCatalog();

        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        addBookPanel = new JPanel();
        addBookPanel.setLayout(new GridLayout(3, 2));
        addBookPanel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        authorField = new JTextField();

        addBookPanel.add(titleLabel);
        addBookPanel.add(titleField);
        addBookPanel.add(authorLabel);
        addBookPanel.add(authorField);

        JButton addButton = new JButton("Add Book");
        addButton.setBackground(Color.GREEN);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                if (!title.isEmpty() && !author.isEmpty()) {
                    libraryCatalog.addBook(new Book(title, author));
                    displayBooks();
                    titleField.setText("");
                    authorField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter both title and author.");
                }
            }
        });

        addBookPanel.add(addButton);

        tabbedPane.addTab("Add Book", addBookPanel);

        searchBookPanel = new JPanel();
        searchBookPanel.setLayout(new GridLayout(4, 2));
        searchBookPanel.setBackground(Color.LIGHT_GRAY);

        JLabel searchLabel = new JLabel("Search:");
        searchField = new JTextField();

        searchBookPanel.add(searchLabel);
        searchBookPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(Color.CYAN);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                if (!searchText.isEmpty()) {
                    Book foundBook = libraryCatalog.searchByTitle(searchText);
                    if (foundBook != null) {
                        displaySearchResult(foundBook);
                    } else {
                        displaySearchResult(null);
                    }
                }
            }
        });

        searchBookPanel.add(searchButton);

        JButton checkoutButton = new JButton("Check Out");
        checkoutButton.setBackground(Color.ORANGE);
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                if (!searchText.isEmpty()) {
                    if (libraryCatalog.checkOutBook(searchText)) {
                        JOptionPane.showMessageDialog(null, "Book checked out successfully.");
                        displayBooks();
                    } else {
                        JOptionPane.showMessageDialog(null, "Book is already checked out or not found.");
                    }
                }
            }
        });
        searchBookPanel.add(checkoutButton);

        JButton returnButton = new JButton("Return");
        returnButton.setBackground(Color.YELLOW);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                if (!searchText.isEmpty()) {
                    if (libraryCatalog.returnBook(searchText)) {
                        JOptionPane.showMessageDialog(null, "Book returned successfully.");
                        displayBooks();
                    } else {
                        JOptionPane.showMessageDialog(null, "Book is already available or not found.");
                    }
                }
            }
        });
        searchBookPanel.add(returnButton);

        tabbedPane.addTab("Search Book", searchBookPanel);

        add(tabbedPane, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        displayBooks();
    }

    private void displayBooks() {
        StringBuilder sb = new StringBuilder();
        sb.append("Library Catalog:\n");
        for (Book book : libraryCatalog.getBooks()) {
            sb.append(book.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void displaySearchResult(Book book) {
        if (book != null) {
            displayArea.setText(book.toString());
        } else {
            displayArea.setText("Book not found.");
        }
    }

    public static void main(String[] args) {
        LibraryManagementSystem libraryManagementSystem = new LibraryManagementSystem();
        libraryManagementSystem.setVisible(true);
    }
}