<?php
$db_name = "adb193970_ca2c";
$mysql_username = "udb_8bf4a9c85b";
$mysql_password = "7689dadd04bd7e73e7c12dcba78438";
$server_name = "mysql02.kudowahost.beep.pl"; 
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);

if($conn) {
	echo "success";
	
}

?>