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
            this.dvgGroup = new System.Windows.Forms.DataGridView();
            this.dvgChild = new System.Windows.Forms.DataGridView();
            this.button1 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dvgGroup)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dvgChild)).BeginInit();
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
            // dvgGroup
            // 
            this.dvgGroup.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dvgGroup.Location = new System.Drawing.Point(34, 124);
            this.dvgGroup.Name = "dvgGroup";
            this.dvgGroup.RowHeadersWidth = 51;
            this.dvgGroup.RowTemplate.Height = 24;
            this.dvgGroup.Size = new System.Drawing.Size(325, 238);
            this.dvgGroup.TabIndex = 2;
            // 
            // dvgChild
            // 
            this.dvgChild.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dvgChild.Location = new System.Drawing.Point(499, 124);
            this.dvgChild.Name = "dvgChild";
            this.dvgChild.RowHeadersWidth = 51;
            this.dvgChild.RowTemplate.Height = 24;
            this.dvgChild.Size = new System.Drawing.Size(325, 238);
            this.dvgChild.TabIndex = 3;
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
            this.Controls.Add(this.dvgChild);
            this.Controls.Add(this.dvgGroup);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "Form1";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.dvgGroup)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dvgChild)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.DataGridView dvgGroup;
        private System.Windows.Forms.DataGridView dvgChild;
        private System.Windows.Forms.Button button1;
    }
}

