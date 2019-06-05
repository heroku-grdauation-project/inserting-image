<?php
require_once("dataset.php");
$submit = new dataset();
$submit->$u_id = $_POST['u_id'];
$submit->$image = $_POST['image'];
$submit->$Address =$_POST['Address'];
$submit->$result_id = $_POST['result_id'];
$submit->$name = $_POST['name'];

$submit->insert($_POST['u_id'],$_POST['image'],$_POST['Address'],$_POST['result_id'],$_POST['name']);
?>