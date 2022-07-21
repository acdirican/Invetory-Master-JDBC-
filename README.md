# Inventory Master using JDBC

This is a simple inventory managment software based on JDBC (DriverManager). Its DBMS is MySQL. 
It is a Maven project. 

Before running the project, you first need to install MySQL (port: 3306) and then create the inventorymaster_db using the SQL import file given in the project folder.

For now, the project only includes  command line interface.
List of Commands:

<ul>
  <li>help</li>
  <li>exit </li>
  <li>list</li>
  <li>list_morethan [QUANTITY] </li>
  <li>list_lessthan [QUANTITY]</li>
  <li>list_equals [QUANTITY]</li>
  <li>list_depleteds </li>
  <li>find [PRODUCT_NAME] </li>
  <li>update [ID] </li>
  <li>fetch [INDEX] </li>
  <li>add</li>
  <li>delete [ID] </li>
  <li>delete_all [ID]+ </li>
  <li>meta</li>
</ul>
