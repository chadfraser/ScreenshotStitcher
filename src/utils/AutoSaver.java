//package utils;
//
//import main.MainFrame;
//import serialize.StoredData;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//
//public abstract class AutoSaver extends Thread {
//    private MainFrame mainFrame;
//    private BufferedImage lastSavedImage;
//    private Color lastSavedBackgroundColor;
//    private String storedImageFileName;
//
//    AutoSaver(MainFrame mainFrame, String threadName) {
//        super(threadName);
//        this.mainFrame = mainFrame;
//        setDaemon(true);
//    }
//
//    public void run() {
//        while (true) {
//            try {
//                Thread.sleep(6000);
//            } catch (InterruptedException ignore) { }
//
//            String newFileName = mainFrame.getSavePanel().getDataFileNameText();
//            if (storedFileName == null || newFileName == null || newFileName.equals(".ser") ||
//                    (newFileName.contains(".") && !newFileName.endsWith(".ser"))) {
//                continue;
//            }
//            if (!newFileName.endsWith(".ser")) {
//                newFileName = newFileName + ".ser";
//            }
//
//            if (!storedFileName.equals(newFileName)) {
//                int answer = JOptionPane.showConfirmDialog(mainFrame,
//                        "The filename has been changed from " + storedFileName + " to " + newFileName + "\n" +
//                                "Auto-save to this new file location?",
//                        "File Auto-save Confirmation",
//                        JOptionPane.OK_CANCEL_OPTION);
//                if (answer == JOptionPane.OK_OPTION) {
//                    //
//                } else {
//                    //
//                }
//            }
//
//            MainFrame.LOCK.lock();
//            try {
//                File outputFile = new File(storedFileName);
//                if (!outputFile.exists()) {
//                    StoredData.serializeData(mainFrame, storedFileName);
//                }
////        } catch (IOException e) {
////            e.printStackTrace();
//            } finally {
//                MainFrame.LOCK.unlock();
//            }
//        }
//    }
//}
