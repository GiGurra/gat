import os
import glob
import zipfile

############################################################
############# Script Configuration

# Search here for jar files
libFolders = [ 
    os.getenv("HOME") + '/.ivy2/cache/org.jogamp.gluegen/gluegen-rt/jars',
    os.getenv("HOME") + '/.ivy2/cache/org.jogamp.jogl/jogl-all/jars',
    os.getenv("HOME") + '/.ivy2/cache/org.jogamp.jogl/joal-all/jars',
    os.getenv("HOME") + '/.ivy2/cache/org.jogamp.jogl/jocl-all/jars'
]

############################################################
############# Script

# Find all source jar files
jars = []
for libFolder in libFolders:
    files = glob.glob(libFolder + "/*-natives-*.jar")
    for file in files:
        jars.append(file)

# Create target folder if it does not exist
nativesFolder = 'src/main/resources/natives'
if not os.path.exists(nativesFolder):
    os.makedirs(nativesFolder)

# Put the natives where they belong (horrible joke ^^)
for jar in jars:
    tgtFolderShortName = jar.split("-natives-")[1].split(".jar")[0]
    tgtFolder = nativesFolder + '/' + tgtFolderShortName
    print("Extracting " + jar + " --> " + tgtFolder)
    with zipfile.ZipFile(jar, "r") as z:
        z.extractall(tgtFolder)
