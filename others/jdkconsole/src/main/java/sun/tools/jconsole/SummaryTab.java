/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.tools.jconsole;

import java.awt.*;
import java.io.*;
import java.lang.management.*;
import java.lang.reflect.*;
import java.net.URL;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import sun.tools.jconsole.MemoryTab.PoolPlotter;

import static sun.tools.jconsole.Formatter.*;
import static sun.tools.jconsole.Resources.*;
import static sun.tools.jconsole.Utilities.*;

@SuppressWarnings("serial")
class SummaryTab extends Tab {
    private static final String cpuUsageKey = "cpu";
    private static final String cpuUsageName = getText("CPU Usage");
    private static final String cpuUsageFormat = "CPUUsageFormat";

    private static final String newDivider =   "<tr><td colspan=4><font size =-1><hr>";
    private static final String newTable =     "<tr><td colspan=4 align=left><table cellpadding=1>";
    private static final String newLeftTable = "<tr><td colspan=2 align=left><table cellpadding=1>";
    private static final String newRightTable =  "<td colspan=2 align=left><table cellpadding=1>";
    private static final String endTable = "</table>";

    private static final int CPU_DECIMALS = 1;

    private CPUOverviewPanel overviewPanel;
    private DateFormat headerDateTimeFormat;
    private String pathSeparator = null;
    HTMLPane info;

    private static class Result {
	long upTime = -1L;
	long processCpuTime = -1L;
	long timeStamp;
	int nCPUs;
	String summary;
    }

    public static String getTabName() {
	return Resources.getText("SummaryTab.tabName");
    }

    public SummaryTab(VMPanel vmPanel) {
	super(vmPanel, getTabName());

	setLayout(new BorderLayout());

	info = new HTMLPane();
	setAccessibleName(info, getTabName());
	add(new JScrollPane(info));

	headerDateTimeFormat =
	    getDateTimeFormat("SummaryTab.headerDateTimeFormat");
    }

    public SwingWorker<?, ?> newSwingWorker() {
	return new SwingWorker<Result, Object>() {
	    public Result doInBackground() {
		return formatSummary();
	    }


	    protected void done() {
		try {
		    Result result = get();
		    if (result != null) {
			info.setText(result.summary);
			if (overviewPanel != null &&
			    result.upTime > 0L &&
			    result.processCpuTime >= 0L) {

			    overviewPanel.updateCPUInfo(result);
			}
		    }
		} catch (InterruptedException ex) {
		} catch (ExecutionException ex) {
		    if (JConsole.isDebug()) {
			ex.printStackTrace();
		    }
		}
	    }
	};
    }

    StringBuilder buf;

    synchronized Result formatSummary() {
	Result result = new Result();
	ProxyClient proxyClient = vmPanel.getProxyClient();
	if (proxyClient.isDead()) {
	    return null;
	}

	buf = new StringBuilder();
	append("<table cellpadding=1>");

	try {
	    RuntimeMXBean         rmBean     = proxyClient.getRuntimeMXBean();
	    CompilationMXBean     cmpMBean   = proxyClient.getCompilationMXBean();
	    ThreadMXBean          tmBean     = proxyClient.getThreadMXBean();
	    MemoryMXBean          memoryBean = proxyClient.getMemoryMXBean();
	    ClassLoadingMXBean    clMBean    = proxyClient.getClassLoadingMXBean();
	    OperatingSystemMXBean osMBean    = proxyClient.getOperatingSystemMXBean();
            com.sun.management.OperatingSystemMXBean sunOSMBean  =
               proxyClient.getSunOperatingSystemMXBean();

	    append("<tr><td colspan=4>");
	    append("<center><b>" + getText("SummaryTab.tabName") + "</b></center>");
	    String dateTime =
		headerDateTimeFormat.format(System.currentTimeMillis());
	    append("<center>" + dateTime + "</center>");

	    append(newDivider);

	    {  // VM info
		append(newLeftTable);
		append("Connection name", vmPanel.getDisplayName());
		append("Virtual Machine",
		       getText("SummaryTab.vmVersion",
			       rmBean.getVmName(), rmBean.getVmVersion()));
		append("Vendor", rmBean.getVmVendor());
		append("Name", rmBean.getName());
		append(endTable);

		append(newRightTable);
		result.upTime = rmBean.getUptime();
		append("Uptime", formatTime(result.upTime));
		if (sunOSMBean != null) {
		    result.processCpuTime = sunOSMBean.getProcessCpuTime();
		    float cpuUsedTime=(float)sunOSMBean.getProcessCpuTime()/1000000000;
		    System.out.println("CPU处理时间："+cpuUsedTime+"秒");                           //CPU处理时间
		    append("Process CPU time", formatNanoTime(result.processCpuTime));
		}

		if (cmpMBean != null) {
		    append("JIT compiler", cmpMBean.getName());
		    append("Total compile time",
			   cmpMBean.isCompilationTimeMonitoringSupported()
				    ? formatTime(cmpMBean.getTotalCompilationTime())
				    : getText("Unavailable"));
		} else {
		    append("JIT compiler", getText("Unavailable"));
		}
		append(endTable);
	    }

	    append(newDivider);

	    {  // Threads and Classes
		append(newLeftTable);
		int tlCount = tmBean.getThreadCount();
		int tdCount = tmBean.getDaemonThreadCount();
		int tpCount = tmBean.getPeakThreadCount();
		long ttCount = tmBean.getTotalStartedThreadCount();
		System.out.println("当前线程个数： "+tlCount);                //当前线程数
		String[] strings1 = formatLongs(tlCount, tpCount,
						tdCount, ttCount);
		append("Live Threads",          strings1[0]);
		append("Peak",                  strings1[1]);
		append("Daemon threads",        strings1[2]);
		append("Total threads started", strings1[3]);
		append(endTable);

		append(newRightTable);
		long clCount = clMBean.getLoadedClassCount();
		long cuCount = clMBean.getUnloadedClassCount();
		long ctCount = clMBean.getTotalLoadedClassCount();
		String[] strings2 = formatLongs(clCount, cuCount, ctCount);
		append("Current classes loaded", strings2[0]);
		append("Total classes loaded",   strings2[2]);
		append("Total classes unloaded", strings2[1]);
		append(null, "");
		append(endTable);
	    }

	    append(newDivider);

	    {  // Memory
		MemoryUsage u = memoryBean.getHeapMemoryUsage();

		append(newLeftTable);
		String[] strings1 = formatKByteStrings(u.getUsed(), u.getMax());
		//System.out.println("堆内存使用大小："+u.getUsed()/1024+"Kb");                   ///////堆内存；
		append("Current heap size", strings1[0]);
		append("Maximum heap size", strings1[1]);
		append(endTable);

		append(newRightTable);
		String[] strings2 = formatKByteStrings(u.getCommitted());
		System.out.println("分配的内存："+u.getCommitted()/1024+"Kb");                   ////分配的内存
		append("Committed memory",  strings2[0]);
		
		append("SummaryTab.pendingFinalization.label",
		       getText("SummaryTab.pendingFinalization.value",
			       memoryBean.getObjectPendingFinalizationCount()));
		append(endTable);

		append(newTable);
		Collection<GarbageCollectorMXBean> garbageCollectors =
					    proxyClient.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean garbageCollectorMBean : garbageCollectors) {
		    String gcName = garbageCollectorMBean.getName();
		    long gcCount = garbageCollectorMBean.getCollectionCount();
		    long gcTime = garbageCollectorMBean.getCollectionTime();

		    append("Garbage collector",
			   getText("GcInfo", gcName, gcCount,
				   (gcTime >= 0) ? formatTime(gcTime)
						 : getText("Unavailable")),
			   4);
		}
		append(endTable);
	    }

	    append(newDivider);

	    {  // Operating System info
		append(newLeftTable);
		String osName = osMBean.getName();
		String osVersion = osMBean.getVersion();
		String osArch = osMBean.getArch();
		result.nCPUs = osMBean.getAvailableProcessors();
		append("Operating System", osName + " " + osVersion);
		append("Architecture", osArch);
		append("Number of processors", result.nCPUs+"");

		if (pathSeparator == null) {
		    // Must use separator of remote OS, not File.pathSeparator
		    // from this local VM. In the future, consider using
		    // RuntimeMXBean to get the remote system property.		    
		    pathSeparator = osName.startsWith("Windows ") ? ";" : ":";
		}

		if (sunOSMBean != null) {
		    String[] kbStrings1 =
			formatKByteStrings(sunOSMBean.getCommittedVirtualMemorySize());
         
		    String[] kbStrings2 =
			formatKByteStrings(sunOSMBean.getTotalPhysicalMemorySize(),
					   sunOSMBean.getFreePhysicalMemorySize(),
					   sunOSMBean.getTotalSwapSpaceSize(),
					   sunOSMBean.getFreeSwapSpaceSize());

		    append("Committed virtual memory", kbStrings1[0]);
		    append(endTable);
		    
		    append(newRightTable);
		    append("Total physical memory", kbStrings2[0]);
		    
		    append("Free physical memory",  kbStrings2[1]);
		    append("Total swap space",      kbStrings2[2]);
		    
		    append("Free swap space",       kbStrings2[3]);
		    
		    /*long usedMemorylong=(sunOSMBean.getTotalPhysicalMemorySize()-sunOSMBean.getFreePhysicalMemorySize())/(1024);
		    System.out.println("VM总物理内存-空闲物理内存："+usedMemorylong+"Kb");                    // 不着调
		    MemoryUsage mu = null;
		    MemoryUsage nu = null;
		    mu = proxyClient.getMemoryMXBean().getNonHeapMemoryUsage();
		    nu = proxyClient.getMemoryMXBean().getHeapMemoryUsage();
		    //long uptime= proxyClient.getUptime();
		    //System.out.println(uptime);
		    System.out.println("所用非堆栈内存："+mu.getUsed()/1024+"Kb");
		    System.out.println("所用堆栈内存："+nu.getUsed()/1024+"Kb");
		    long jjjj=mu.getUsed()+nu.getUsed();
		    System.out.println("堆栈内存+非堆栈内存："+jjjj/1024+"Kb");  */
		    System.out.println("分配的虚拟内存："+sunOSMBean.getCommittedVirtualMemorySize()/1024+"Kb"); //分配的虚拟内存
		    long memoryTotalCommited=sunOSMBean.getCommittedVirtualMemorySize()/1024+memoryBean.getHeapMemoryUsage().getCommitted()/1024;
            System.out.println("内存使用总量："+memoryTotalCommited+"Kb");		    
		}

		append(endTable);
	    }

	    append(newDivider);

	    {  // VM arguments and paths
		append(newTable);
		String args = "";
		java.util.List<String> inputArguments = rmBean.getInputArguments();
		for (String arg : inputArguments) {
		    args += arg + " ";
		}
		append("VM arguments", args, 4);
		append("Class path",   rmBean.getClassPath(), 4);
		append("Library path", rmBean.getLibraryPath(), 4);
		append("Boot class path",
		       rmBean.isBootClassPathSupported()
				    ? rmBean.getBootClassPath()
				    : getText("Unavailable"),
		       4);
		append(endTable);
	    }
  	} catch (IOException e) {
	    if (JConsole.isDebug()) {
		e.printStackTrace();
	    }
	    proxyClient.markAsDead();
	    return null;
	} catch (UndeclaredThrowableException e) {
	    if (JConsole.isDebug()) {
		e.printStackTrace();
	    }
	    proxyClient.markAsDead();
	    return null;
	}

	append("</table>");

	result.timeStamp = System.currentTimeMillis();
	result.summary = buf.toString();

	return result;
    }
    
    

    private synchronized void append(String str) {
	buf.append(str);
    }

    void append(String label, String value) {
        append(newRow((label != null) ? getText(label) : label, value));
    }

    private void append(String label, String value, int columnPerRow) {
	if (columnPerRow == 4 && pathSeparator != null) {
	    value = value.replace(pathSeparator,
				  "<b></b>" + pathSeparator);
	}
	append(newRow(getText(label), value, columnPerRow));
    }

    void append(String label1, String value1,
		String label2, String value2) {
	append(newRow(getText(label1), value1,
		      getText(label2), value2));
    }

    OverviewPanel[] getOverviewPanels() {
	if (overviewPanel == null) {
	    overviewPanel = new CPUOverviewPanel();
	}
	return new OverviewPanel[] { overviewPanel };
    }

    private static class CPUOverviewPanel extends OverviewPanel {
	private long prevUpTime, prevProcessCpuTime;

	CPUOverviewPanel() {
	    super(getText("CPU Usage"), cpuUsageKey, cpuUsageName, Plotter.Unit.PERCENT);
	    getPlotter().setDecimals(CPU_DECIMALS);
	}

	public void updateCPUInfo(Result result) {
	    if (prevUpTime > 0L && result.upTime > prevUpTime) {
		// elapsedCpu is in ns and elapsedTime is in ms.
		long elapsedCpu = result.processCpuTime - prevProcessCpuTime;
		long elapsedTime = result.upTime - prevUpTime;
		// cpuUsage could go higher than 100% because elapsedTime
		// and elapsedCpu are not fetched simultaneously. Limit to
		// 99% to avoid Plotter showing a scale from 0% to 200%.
		float cpuUsage =
		    Math.min(99F,
			     elapsedCpu / (elapsedTime * 10000F * result.nCPUs));
        System.out.println("CPU使用率："+cpuUsage+"%");                                ///CPU使用率；
		System.out.println("--------------------------------------");
        getPlotter().addValues(result.timeStamp,
				Math.round(cpuUsage * Math.pow(10.0, CPU_DECIMALS)));
		getInfoLabel().setText(getText(cpuUsageFormat,
					       String.format("%."+CPU_DECIMALS+"f", cpuUsage)));
	    }
	    this.prevUpTime = result.upTime;
	    this.prevProcessCpuTime = result.processCpuTime;
	}
    }



}
