namespace practicalExam
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.dvgBrokerages = new System.Windows.Forms.DataGridView();
            this.dvgClients = new System.Windows.Forms.DataGridView();
            this.button1 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dvgBrokerages)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dvgClients)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(91, 52);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(50, 17);
            this.label1.TabIndex = 0;
            this.label1.Text = "Parent";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(598, 52);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(39, 17);
            this.label2.TabIndex = 1;
            this.label2.Text = "Child";
            // 
            // dvgBrokerages
            // 
            this.dvgBrokerages.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dvgBrokerages.Location = new System.Drawing.Point(34, 124);
            this.dvgBrokerages.Name = "dvgBrokerages";
            this.dvgBrokerages.RowHeadersWidth = 51;
            this.dvgBrokerages.RowTemplate.Height = 24;
            this.dvgBrokerages.Size = new System.Drawing.Size(325, 238);
            this.dvgBrokerages.TabIndex = 2;
            // 
            // dvgClients
            // 
            this.dvgClients.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dvgClients.Location = new System.Drawing.Point(499, 124);
            this.dvgClients.Name = "dvgClients";
            this.dvgClients.RowHeadersWidth = 51;
            this.dvgClients.RowTemplate.Height = 24;
            this.dvgClients.Size = new System.Drawing.Size(325, 238);
            this.dvgClients.TabIndex = 3;
            this.dvgClients.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridViewChild_CellContentClick);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(370, 418);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(117, 59);
            this.button1.TabIndex = 4;
            this.button1.Text = "UPDATE";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(943, 543);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.dvgClients);
            this.Controls.Add(this.dvgBrokerages);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "Form1";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.dvgBrokerages)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dvgClients)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.DataGridView dvgBrokerages;
        private System.Windows.Forms.DataGridView dvgClients;
        private System.Windows.Forms.Button button1;
    }
}

