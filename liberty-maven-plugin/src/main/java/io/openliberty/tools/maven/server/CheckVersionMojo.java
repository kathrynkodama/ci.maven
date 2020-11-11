package io.openliberty.tools.maven.server;

import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import io.openliberty.tools.common.plugins.util.InstallFeatureUtil;
import io.openliberty.tools.common.plugins.util.PluginExecutionException;
import io.openliberty.tools.common.plugins.util.InstallFeatureUtil.ProductProperties;
import io.openliberty.tools.maven.InstallFeatureSupport;

@Mojo(name = "version")
public class CheckVersionMojo extends InstallFeatureSupport {

    @Override
    protected void init() throws MojoExecutionException, MojoFailureException {
        this.skipDownload = true;
        super.init();
    }

    /*
     * (non-Javadoc)
     * @see org.codehaus.mojo.pluginsupport.MojoSupport#doExecute()
     */
    @Override
    protected void doExecute() throws Exception {
        checkVersion();
    }

    private void checkVersion() throws PluginExecutionException {
        String version = null;
        if (installDirectory != null && installDirectory.exists()) {
            List<ProductProperties> propertiesList = InstallFeatureUtil.loadProperties(installDirectory);
            log.debug("Retrieving version from install directory");
            version = InstallFeatureUtil.getOpenLibertyVersion(propertiesList);
        } else if (assemblyArtifact.getVersion() != null) {
            log.debug("Retrieving version from assemblyArtifact");
            version = assemblyArtifact.getVersion();
        }
        if (version != null) {
            log.info("Liberty version: " + version);
        } else {
            log.warn("Unable to resolve liberty version");
        }
    }
    
}
