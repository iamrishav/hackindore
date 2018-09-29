<?php

require_once '../../db_functions.php';
$db = new DB_Functions();

if(isset($_POST['title']) && isset($_POST['description']))
{
    
    $title = $_POST['title'];
    $description = $_POST['description'];
    
    
    $result = $db->insertNewNoti( $title , $description );
    
    if($result)
     echo json_encode("Add Notice success !");
     
     else
       echo json_encode("Error while write to database");
    
}
else
{
    echo json_encode("Required parameters (title , description) is missing");
}
?>