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

            queryParent = "SELECT * FROM Groupp"; //fill parent table
            queryChild = "SELECT * FROM Child"; //fill child table


            // SQL Data Adapters & Data Set
            daChild = new SqlDataAdapter(queryChild, conn);
            daParent = new SqlDataAdapter(queryParent, conn);

            ds = new DataSet();
            daParent.Fill(ds, "Groupp"); //parent table name
            daChild.Fill(ds, "Child"); // child table name

            // fill in insert, update and delete commands
            cmdBuilder = new SqlCommandBuilder(daChild);

            // Data Relation (parent-child) added to Data Set
            ds.Relations.Add("groupchild", ds.Tables["Groupp"].Columns["groupId"], ds.Tables["Child"].Columns["groupId"]);
            // parent-child, parent table name, parent primary key, child table name, child foreign key to parent

            // fill into DataGridViews using Data Binding
            bsParent = new BindingSource();
            bsParent.DataSource = ds.Tables["Groupp"]; //parent table name
            bsChild = new BindingSource(bsParent, "groupchild"); // name of parent-child above

            this.dvgGroup.DataSource = bsParent;
            this.dvgChild.DataSource = bsChild;

            cmdBuilder.GetUpdateCommand();
        }

        string getConnectionString()
        {
            return "Data Source=DESKTOP-B77GF39;Initial Catalog=toys;Integrated Security=true;";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                daChild.Update(ds, "Child"); //child table name
            }
            catch (SqlException ex)
            {
                MessageBox.Show(ex.Message);
            }

        }
    }
}
