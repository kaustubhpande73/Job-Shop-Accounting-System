<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Customer</title>
    </head>
    <body>
        <h2>Add Customer</h2>

        <form action="add_cust.jsp" method="post">
            <!-- The form organized in an HTML table for better clarity. -->
            <table border="1">
                <tr>
                    <th colspan="2">Enter the Customer Data:</th>
                </tr>
                <tr>
                    <td>Customer Name:</td>
                    <td><div style="text-align: center;">
                        <input type="text" name="customer_name">
                    </div></td>
                </tr>
                <tr>
                    <td>Customer Address:</td>
                    <td><div style="text-align: center;">
                        <input type="text" name="customer_address">
                    </div></td>
                </tr>
                <tr>
                    <td>Category:</td>
                    <td><div style="text-align: center;">
                        <input type="number" name="category" min="1">
                    </div></td>
                </tr>
                <tr>
                    <td><div style="text-align: center;">
                        <input type="reset" value="Clear">
                    </div></td>
                    <td><div style="text-align: center;">
                        <input type="submit" value="Insert">
                    </div></td>
                </tr>
            </table>
        </form>
    </body>
</html>
