<?php
require "check_connection.php";

$sql = "select * from wp+posts where post_type like 'post';";

$result = mysqli_query($conn, $sql);
$response = array();

echo $result;


?>
