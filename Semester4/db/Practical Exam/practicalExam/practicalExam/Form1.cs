using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace practicalExam
{
    public partial class Form1 : Form
    {
        SqlConnection conn;
        SqlDataAdapter daParent;
        SqlDataAdapter daChild;
        DataSet ds;
        BindingSource bsParent;
        BindingSource bsChild;

        SqlCommandBuilder cmdBuilder;

        string queryParent;
        string queryChild;

        public Form1()
        {
            InitializeComponent();
            FillData();
        }

        void FillData()
        {
            // SQL connection
            conn = new SqlConnection(getConnectionString());

            queryParent = "SELECT * FROM Brokerage"; //fill parent table
            queryChild = "SELECT * FROM Client"; //fill child table

            // SQL Data Adapters & Data Set
            daChild = new SqlDataAdapter(queryChild, conn);
            daParent = new SqlDataAdapter(queryParent, conn);

            ds = new DataSet();
            daParent.Fill(ds, "Brokerage"); //parent table name
            daChild.Fill(ds, "Client"); // child table name

            // fill in insert, update and delete commands
            cmdBuilder = new SqlCommandBuilder(daChild);

            // Data Relation (parent-child) added to Data Set
            ds.Relations.Add("brokerageclient", ds.Tables["Brokerage"].Columns["brokerageId"], ds.Tables["Client"].Columns["brokerageId"]);
            // parent-child, parent table name, parent primary key, child table name, child foreign key to parent

            // fill into DataGridViews using Data Binding
            bsParent = new BindingSource();
            bsParent.DataSource = ds.Tables["Brokerage"]; //parent table name
            bsChild = new BindingSource(bsParent, "brokerageclient"); // name of parent-child above

            this.dvgBrokerages.DataSource = bsParent;
            this.dvgClients.DataSource = bsChild;

            cmdBuilder.GetUpdateCommand();
        }

        string getConnectionString()
        {
            return "Data Source=DESKTOP-B77GF39;Initial Catalog=dbms-practical;Integrated Security=true;";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                daChild.Update(ds, "Client"); //child table name
            }
            catch (SqlException ex)
            {
                MessageBox.Show(ex.Message);
            }

        }

        private void dataGridViewChild_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }
    }
}
