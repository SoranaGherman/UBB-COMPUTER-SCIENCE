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

            queryParent = "SELECT * FROM Company"; //fill parent table
            queryChild = "SELECT * FROM Internship"; //fill child table

            // SQL Data Adapters & Data Set
            daChild = new SqlDataAdapter(queryChild, conn);
            daParent = new SqlDataAdapter(queryParent, conn);

            ds = new DataSet();
            daParent.Fill(ds, "Company"); //parent table name
            daChild.Fill(ds, "Internship"); // child table name

            // fill in insert, update and delete commands
            cmdBuilder = new SqlCommandBuilder(daChild);

            // Data Relation (parent-child) added to Data Set
            ds.Relations.Add("companyinternship", ds.Tables["Company"].Columns["companyId"], ds.Tables["Internship"].Columns["companyId"]);
            // parent-child, parent table name, parent primary key, child table name, child foreign key to parent

            // fill into DataGridViews using Data Binding
            bsParent = new BindingSource();
            bsParent.DataSource = ds.Tables["Company"]; //parent table name
            bsChild = new BindingSource(bsParent, "companyinternship"); // name of parent-child above

            this.dataGridViewParent.DataSource = bsParent;
            this.dataGridViewChild.DataSource = bsChild;

            cmdBuilder.GetUpdateCommand();
        }

        string getConnectionString()
        {
            return "Data Source=DESKTOP-B77GF39;Initial Catalog=internship;Integrated Security=true;";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                daChild.Update(ds, "Internship"); //child table name
            }
            catch (SqlException ex)
            {
                MessageBox.Show(ex.Message);
            }

        }
    }
}
