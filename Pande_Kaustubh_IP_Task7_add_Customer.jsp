<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Query Result</title>
</head>
    <body>
    <%@ page import="Indivisual_Project.DataHandler"%>
    <%@ page import="java.sql.ResultSet"%>
    <%@ page import="java.sql.SQLException"%>
    <%
    // Instantiate the handler responsible for database operations
    DataHandler handler = new DataHandler();

    // Retrieve attribute values passed from the form
    String customerName = request.getParameter("customer_name");
    String customerAddress = request.getParameter("customer_address");
    String categoryString = request.getParameter("category");

    // Simple validation to ensure required fields are filled
    if (customerName.equals("") || customerAddress.equals("") || categoryString.equals("")) {
        // Redirect back to the form if validation fails
        response.sendRedirect("add_customer_form.jsp");
    } else {
        int category = Integer.parseInt(categoryString);
        boolean success = false;
        try {
            // Perform the insert operation
            success = handler.addCustomer(customerName, customerAddress, category);
        } catch(SQLException e) {
            // Handle SQL exception if any
            e.printStackTrace();
        }
        if (!success) { // If the insert operation failed
            %>
                <h2>There was a problem inserting the customer</h2>
            <%
        } else { // Confirm success to the user
            %>
            <h2>The Customer:</h2>

            <ul>
                <li>Name: <%=customerName%></li>
                <li>Address: <%=customerAddress%></li>
                <li>Category: <%=categoryString%></li>
            </ul>

            <h2>Was successfully inserted.</h2>
            
            <a href="get_all_customers.jsp">See all customers.</a>
            <%
        }
    }
    %>
    </body>
</html>
