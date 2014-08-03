
package cn.com.easy.deploy.controller.deployproject;

import java.io.LineNumberReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.deployproject.DeployTaskDTO;
import cn.com.easy.deploy.service.deployproject.deploytask.IDeployTaskService;
import cn.com.easy.deploy.utils.ResponseTypeOutputUtils;

/**
 * 部署任务控制器
 * 
 * @author nibili
 * 
 */
@Controller
@RequestMapping("/deployproject")
public class DeployTaskController {

	private static Logger logger = LoggerFactory.getLogger(DeployTaskController.class);

	@Autowired
	private IDeployTaskService deployTaskService;

	/**
	 * 根据任务id ,获取 部署任务
	 * 
	 * @param request
	 * @param response
	 * @param deployTaskDTO
	 * @param projectId
	 * @param taskDetails
	 */
	@RequestMapping(value = "/getDeployTask/{taskId}")
	public void addDeployTask(HttpServletRequest request, HttpServletResponse response, @PathVariable String taskId) {

		try {
			ResponseTypeOutputUtils.renderJson(response,
					MessageDTO.getMessage(true, "", "获取部署任务成功！", deployTaskService.getDeployTasksByTaskId(taskId)));
		} catch (Exception e) {
			logger.error("获取部署任务异常", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "获取部署任务异常!请重试！" + e.getLocalizedMessage()));
		}
	}

	/**
	 * 
	 * 添加部署任务
	 * 
	 * @param request
	 * @param response
	 * @param deployTaskDTO
	 * @param projectId
	 * @param taskDetail
	 *            [{"id,type"},{"id,type"}]
	 */
	@RequestMapping(value = "/addDeployTask")
	public void addDeployTask(HttpServletRequest request, HttpServletResponse response, DeployTaskDTO deployTaskDTO, String projectId,
			String[] taskDetails) {

		if (StringUtils.isBlank(projectId)) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "项目id不能为空!"));
			return;
		}
		try {
			ResponseTypeOutputUtils.renderJson(response, deployTaskService.addDeployTask(projectId, deployTaskDTO, taskDetails));
		} catch (Exception e) {
			logger.error("添加部署任务异常", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "添加部署任务异常!请重试！" + e.getLocalizedMessage()));
		}
	}

	/**
	 * 删除部署任务
	 * 
	 * @param request
	 * @param response
	 * @param deployFileDTO
	 * @param projectId
	 */
	@RequestMapping(value = "/deleteDeployTask/{deployTaskId}")
	public void deleteDeployCommand(HttpServletRequest request, HttpServletResponse response, @PathVariable String deployTaskId) {

		if (StringUtils.isBlank(deployTaskId)) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "部署任务id不能为空!"));
			return;
		}
		try {
			ResponseTypeOutputUtils.renderJson(response, deployTaskService.deleteDeployTask(deployTaskId));
		} catch (Exception e) {
			logger.error("删除部署任务异常", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "删除部署任务异常!请重试！"));
		}
	}

	/**
	 * 更新部署任务
	 * 
	 * @param request
	 * @param response
	 * @param deployFileDTO
	 * @param projectId
	 */
	@RequestMapping(value = "/updateDeployTask")
	public void updateDeployCommand(HttpServletRequest request, HttpServletResponse response, DeployTaskDTO deployTaskDTO,
			String[] taskDetails) {

		if (null == deployTaskDTO.getId()) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "任务id不能为空!"));
			return;
		}
		try {
			ResponseTypeOutputUtils.renderJson(response, deployTaskService.updateDeployTask(deployTaskDTO, taskDetails));
		} catch (Exception e) {
			logger.error("添加部署任务异常", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "添加部署任务异常!请重试！" + e.getLocalizedMessage()));
		}
	}

	/**
	 * 执行部署任务
	 * 
	 * @param request
	 * @param response
	 * @param deployFileDTO
	 * @param projectId
	 */
	@RequestMapping(value = "/executeTask/{taskId}")
	public void updateDeployCommand(HttpServletRequest request, HttpServletResponse response, @PathVariable Long taskId) {

		try {

			ResponseTypeOutputUtils.renderHtml(response, "<style> body {font: 12px/1.14 \"微软雅黑\", \"宋体\", Arial, "
					+ "sans-serif, Verdana, Tahoma;color: #333;line-height:20px;}</style>");

			ResponseTypeOutputUtils.renderHtml(response, "开始执行任务......<br/>");
			List<LineNumberReader> inputs = deployTaskService.excecutTask(taskId);
			String line;
			for (LineNumberReader input : inputs) {
				// LineNumberReader input = inputs.get(0);
				while ((line = input.readLine()) != null) {
					// logger.info(line);
					ResponseTypeOutputUtils.renderHtml(response, line + "<br/><script>"
							+ "document.body.scrollTop = document.body.scrollHeight;" + " </script>");
				}
			}
		} catch (Exception e) {
			logger.error("执行部署任务异常", e);
			ResponseTypeOutputUtils.renderText(response, "执行部署任务异常:" + e + "<br/>");
		}
		ResponseTypeOutputUtils.renderHtml(response, "任务执行结束!<br/>");
	}
}
