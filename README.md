# Inventory Master using JDBC

This is a simple inventory managment software based on JDBC (DriverManager). Its uses MySQL and preaped using Maven. 

The project allows you to keep track of a product inventory with supplier information. Therefore it includes two entities: Product and Supplier. There is an one-to-many relationship between them.  

Before running the project, you first need to install MySQL (port: 3306) and then create the inventorymaster_db using the SQL import file given in the project folder.

The project has an MVC like architecture in which CLI, Repository and Model are seperated. It also applies repository pattern.

For now, the project only includes  command line interface.

List of Commands:
<ul>
<li>help</li>
<li>exit</li> 
<li>list [product/supplier]</li>
<li>list morethan [QUANTITY] </li>
<li>list lessthan [QUANTITY]</li>
<li>list equals [QUANTITY]</li>
<li>list depleted</li>
<li>find <NAME></li>
<li>update [product/supplier] [ID]</li>
<li>fetch <INDEX></li>
<li>add [product/supplier]</li>
<li>delete [product/supplier] [ID]</li></li>
<li>delete_all [product/supplier]  [ID]+</li>
</ul>
