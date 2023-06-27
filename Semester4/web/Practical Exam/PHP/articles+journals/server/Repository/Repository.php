<?php

class Repository 
{
    private $host = "localhost";
    private $username = "root";
    private $password = "";
    private $database = "exam_wp";
    protected $connection = null;

    public function __construct()
    {
        $this->connect();
    }

    public function __destruct()
    {
        $this->disconnect();
    }

    private function connect(): void
    {
        $this->connection = new mysqli($this->host, $this->username, $this->password, $this->database);
    }

    private function disconnect(): void
    {
        $this->connection->close();
    }
}

?>