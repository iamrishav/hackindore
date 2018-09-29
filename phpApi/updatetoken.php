<?php

require_once 'db_functions.php';
$db = new DB_Functions();

if(isset($_POST['phone']) &&
   isset($_POST['token']) &&
   isset($_POST['isServerToken']))
   
   {
       
       $phone = $_POST['phone'];
       $token = $_POST['token'];       
       $isServerToken = $_POST['isServerToken'];       
       
       $user = $db->insertToken($phone , $token , $isServerToken);
       
       if($user)
           echo json_encode("Token Update Success");
       else
           echo json_encode("Token update Fail");
   }
   else
   {
       echo json_encode("Required Parameters (phone , token , isServerToken ) is Missing");
   }



?>