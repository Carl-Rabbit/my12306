import numpy as np

print(str('重庆'.encode('unicode_escape')).replace('\\\\','%').replace('b','').replace('\'','').upper().replace('%U','%u'))
