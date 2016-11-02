/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aleks.esp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Future;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

@ActionID(
        category = "Build",
        id = "com.aleks.esp.FlashFirmwareAction"
)
@ActionRegistration(
        iconBase = "com/aleks/esp/firmupload.png",
        displayName = "#CTL_FlashFirmwareAction"
)
@ActionReferences({
  @ActionReference(path = "Menu/Tools", position = 0)
  ,
  @ActionReference(path = "Toolbars/Build", position = 250)
  ,
  @ActionReference(path = "Shortcuts", name = "DS-B")
})
@Messages("CTL_FlashFirmwareAction=Flash Firmware")
public final class FlashFirmwareAction implements ActionListener {

  Future<Integer> exitCode;
  InputOutput io = IOProvider.getDefault().getIO("Flash Firmware", false);

  @Override
  public void actionPerformed(ActionEvent e) {
    io.setOutputVisible(true);

    ExecutionDescriptor descriptor = new ExecutionDescriptor()
            .controllable(true).noReset(true).frontWindowOnError(true)
            .showProgress(true)
            .frontWindow(true).frontWindowOnError(true)
            .preExecution(() -> {
              io.getOut().println("make flash\n");

            })
            .inputOutput(io);

    ExecutionService exeService = null;
    exeService = ExecutionService.newService(
            new FlashProcessLaunch("make", "flash"),
            descriptor, "Flash Firmware");

    exitCode = exeService.run();

  }

}
