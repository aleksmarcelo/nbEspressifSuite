/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aleks.esp;

import java.io.File;
import java.util.concurrent.Callable;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.windows.TopComponent;

class FlashProcessLaunch implements Callable<Process> {
  
  
  
  private final String[] commandLine;
  private  String directory = null;



  public void setDirectory(String directory) {
    this.directory = directory;
  }
  
  public FlashProcessLaunch(String... commandLine) {
    this.commandLine = commandLine;
  }
  
  public Process call() throws Exception {
    ProcessBuilder pb = new ProcessBuilder(commandLine);
    
    directory = getProjectDirectory();
    if (directory == null)
      pb.directory(new File(System.getProperty("user.home"))); //NOI18N
    else
      pb.directory(new File(directory)); 
   
    pb.redirectErrorStream(true);
    pb.redirectOutput();
    
    return pb.start();
  }
  
  private String getProjectDirectory() {
    try {
      String filePath = null;

      Project project = OpenProjects.getDefault().getMainProject();

      if (project == null) {
        TopComponent activeTC = TopComponent.getRegistry().getActivated();
        DataObject dataLookup = activeTC.getLookup().lookup(DataObject.class);
        filePath = FileUtil.toFile(dataLookup.getPrimaryFile()).getAbsolutePath();
      } else {
        FileObject pd = project.getProjectDirectory();
        filePath = pd.getPath();
      }

      return filePath;
    } catch (Exception e) {
      //ignore the exception
      return null;
    }
  }
  
  
}