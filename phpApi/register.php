<?php

require_once 'db_functions.php';
$db = new DB_Functions();



$response = array();

if(isset($_POST['phone']) &&
   isset($_POST['name']) &&
   isset($_POST['birthdate']) &&
   isset($_POST['address']))
{
    $phone = $_POST['phone'];
    $name = $_POST['name'];
    $birthdate = $_POST['birthdate'];
    $address = $_POST['address'];
    
    if($db->checkExistsUser ($phone))
    
    {
        
        $response["error_msg"] = "User already existed with ".$phone ;
        echo json_encode($response);
    }
    else
    {
        
        
        $user = $db->registerNewUser($phone,$name,$birthdate,$address);
        if($user)
        {
            $response["phone"] = $user["Phone"];
            $response["name"] = $user["Name"];
            $response["birthdate"] = $user["Birthdate"];
            $response["address"] = $user["Address"];
            
            echo json_encode($response);
            
        }
        
        else
        {
            
           $response["error_msg"] = "unknown error occured in Registration" ;
        echo json_encode($response);  
        
        
        }
    }
    
}

else {
    
    $response["error_msg"] = "Required parameter (phone , name , birthdate , address) is missing";
    echo json_encode($response);
}



?>