<?php
require "db.php";
class dataset{
    public $id;
    public $u_id;
    public $image;
    public $Address;
    public $result_id;
    public $name;

    public function insert($us_id,$img,$addres,$reid,$name)
    {
        $DBobject = new DB();
        $sql = "INSERT INTO dataset (`u_id`, `image`, `Address`, `result_id`, `name`)  VALUES
        ('".$us_id."','".$img."','".$addres."','".$reid."','".$name."')";
        $DBobject->connect();
        $DBobject->execute($sql);
        $DBobject->disconnect();
    }
    /*
    public function select(){
        $DBobject = new DB();
        $sql = "SELECT * FROM dataset WHERE id = '".$this->id."' ";
        $DBobject->connect();
        $result = $DBobject->execute($sql);
        while ($row = mysqli_fetch_array($result)){
            echo $row['id'];
            echo $row['u_id'];
            echo $row['image'];
            echo $row['imagesrclon'];
            echo $row['imagesrclat'];
            echo $row['result_id'];
            echo $row['name'];
        }
        $DBobject->disconnect();
    }
    public function update(){
        $DBobject = new DB();
        $sql = "UPDATE dataset SET result_id='".$this->result_id."' WHERE id = '".$this->id."'";
        $DBobject->connect();
        $DBobject->execute($sql);
        $DBobject->disconnect();

    }
*/
}
?>