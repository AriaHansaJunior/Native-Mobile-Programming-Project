<?php
require_once('koneksi.php');

$sql = "SELECT * FROM student";
$result = $con->query($sql);

$students = array();

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        array_push($students, $row);
    }
}

echo json_encode($students);
$con->close();
?>