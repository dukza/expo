package expo.core;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import expo.core.interfaces.Module;
import expo.core.interfaces.Package;
import expo.core.interfaces.ViewManager;

/**
 * Builder for {@link ModuleRegistry}. Override this class to add some custom
 * modules from outside of {@link Package} ecosystem.
 */
public class ModuleRegistryProvider {
  private List<Package> mPackages;

  public ModuleRegistryProvider(List<Package> initialPackages) {
    mPackages = initialPackages;
  }

  protected List<Package> getPackages() {
    return mPackages;
  }

  public ModuleRegistry get(Context context) {
    return new ModuleRegistry(
            createInternalModules(context),
            createExportedModules(context),
            createViewManagers(context)
    );
  }

  public Collection<Module> createInternalModules(Context context) {
    Collection<Module> internalModules = new ArrayList<>();
    for (Package pkg : getPackages()) {
      internalModules.addAll(pkg.createInternalModules(context));
    }
    return internalModules;
  }

  public Collection<ExportedModule> createExportedModules(Context context) {
    Collection<ExportedModule> exportedModules = new ArrayList<>();
    for (Package pkg : getPackages()) {
      exportedModules.addAll(pkg.createExportedModules(context));
    }
    return exportedModules;
  }

  public Collection<ViewManager> createViewManagers(Context context) {
    Collection<ViewManager> viewManagers = new ArrayList<>();
    for (Package pkg : getPackages()) {
      viewManagers.addAll(pkg.createViewManagers(context));
    }
    return viewManagers;
  }
}
