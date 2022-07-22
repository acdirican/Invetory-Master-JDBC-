# Inventory Master using JDBC

This is a simple inventory managment software based on JDBC (DriverManager), papared by Maven. Its uses MySQL. 

The project allows you to keep track of a product inventory with supplier information. Therefore, it includes two entities: Product and Supplier. There is an one-to-many relationship between them.  

In terms JDBC features, the project runs queries using Statement, PreparedStatement and CallableStatement (for stored prosedures).

The project has an MVC like architecture in which CLI, Repository and Model are seperated. It also applies repository pattern.

Before running the project, you first need to install MySQL (port: 3306) and then create the inventorymaster_db using the SQL import file given in the project folder.

For now, the project only includes  command line interface.

List of Commands:
<ul>
<li>help</li>
<li>exit</li> 
<br/>
<li>list [product/supplier]</li>
<li>list morethan [QUANTITY] </li>
<li>list lessthan [QUANTITY]</li>
<li>list equals [QUANTITY]</li>
<li>list depleted</li>
<br/>
<li>find [product/supplier] [NAME]</li>
<li>getindex [product/supplier] [INDEX]</li>
<li>getid [product/supplier] [ID]</li>
<br/>
<li>update [product/supplier] [ID]</li>
<li>add [product/supplier]</li>
<li>delete [product/supplier] [ID]</li></li>
<li>delete_all [product/supplier]  [ID]+</li>
</ul>
