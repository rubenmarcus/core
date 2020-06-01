package com.dotmarketing.portlets.contentlet.transform.strategy;

import static com.dotmarketing.portlets.contentlet.transform.strategy.TransformOptions.LOAD_META;
import static com.dotmarketing.portlets.contentlet.transform.strategy.TransformOptions.USE_ALIAS;
import static com.dotmarketing.portlets.fileassets.business.FileAssetAPI.DESCRIPTION;
import static com.dotmarketing.portlets.fileassets.business.FileAssetAPI.FILE_NAME_FIELD;
import static com.dotmarketing.portlets.fileassets.business.FileAssetAPI.META_DATA_FIELD;
import static com.dotmarketing.portlets.fileassets.business.FileAssetAPI.MIMETYPE_FIELD;
import static com.dotmarketing.portlets.fileassets.business.FileAssetAPI.TITLE_FIELD;
import static com.dotmarketing.portlets.fileassets.business.FileAssetAPI.UNDERLYING_FILENAME;
import static com.dotmarketing.util.UtilHTML.getIconClass;
import static com.dotmarketing.util.UtilHTML.getStatusIcons;
import static com.dotmarketing.util.UtilMethods.getFileExtension;
import static com.dotmarketing.util.UtilMethods.isNotSet;
import static com.dotmarketing.util.UtilMethods.isSet;
import static com.liferay.util.StringPool.BLANK;

import com.dotmarketing.beans.Identifier;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.exception.DotSecurityException;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.portlets.fileassets.business.FileAsset;
import com.dotmarketing.util.Logger;
import com.liferay.portal.model.User;
import java.util.Map;
import java.util.Set;

public class FileAssetViewStrategy extends WebAssetStrategy<FileAsset> {

    FileAssetViewStrategy(final TransformToolbox toolBox) {
        super(toolBox);
    }

    @Override
    public FileAsset fromContentlet(final Contentlet contentlet) {
        return toolBox.fileAssetAPI.fromContentlet(contentlet);
    }

    @Override
    public Map<String, Object> transform(final FileAsset fileAsset, final Map<String, Object> map,
            final Set<TransformOptions> options, final  User user)
            throws DotDataException, DotSecurityException {

        if(isNotSet(fileAsset.getTitle())) {
            final Identifier identifier = toolBox.identifierAPI.find(fileAsset.getIdentifier());
            map.put(TITLE_FIELD, identifier.getAssetName());
        }
        map.put(MIMETYPE_FIELD, fileAsset.getMimeType());
        map.put("name", fileAsset.getFileName());
        map.put("size", fileAsset.getFileSize());
        final String description = fileAsset.getStringProperty(DESCRIPTION);
        map.put(DESCRIPTION, isSet(description) ? description : BLANK );
        map.put("type", "file_asset");
        map.put(UNDERLYING_FILENAME, fileAsset.getUnderlyingFileName());
        if(options.contains(LOAD_META)) {
            map.put(META_DATA_FIELD, fileAsset.getMetaData());
        }
        map.put("path", fileAsset.getPath());
        final String parent = fileAsset.getParent();
        map.put("parent",  isSet(parent) ? parent : BLANK );
        map.put("width", fileAsset.getWidth());
        map.put("height", fileAsset.getHeight());

        map.put("extension", getFileExtension(fileAsset.getUnderlyingFileName()));
        try {
            map.put("__icon__", getIconClass(fileAsset));
        }catch (Exception e){
            Logger.warn(FileAssetViewStrategy.class,"Failed to get icon.",e);
        }
        try {
            map.put("statusIcons", getStatusIcons(fileAsset));
        }catch (Exception e){
            Logger.warn(FileAssetViewStrategy.class," Failed to get status icon.",e);
        }
        if(options.contains(USE_ALIAS)) {
            map.put(FILE_NAME_FIELD, fileAsset.getFileName());
            map.put("fileSize", fileAsset.getFileSize());
        }
        map.put("isContentlet", true);
        return map;
    }
}