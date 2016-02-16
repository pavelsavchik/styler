package com.satch.elfinder.command
/**
 * @author Sudhir Nimavat
 */
class ElfinderLsCommand extends ElfinderBaseCommand {

	@Override
	void execute() {
		String target = params['target']

		List files = elFinderFileManager.scanDir(unhash(target))
		List resp = files.collect { it.name }

		putResponse("list", resp)
	}
}
